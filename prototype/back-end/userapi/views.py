from django import forms
from django.contrib.auth.tokens import default_token_generator
from django.http import HttpResponse
from django.shortcuts import render, render_to_response

from django.contrib.auth.models import User
from django.template.loader import get_template
from django.utils.encoding import force_text
from django.utils.http import urlsafe_base64_decode
from django.views.generic import FormView
from rest_framework import viewsets, filters
from rest_framework.authtoken.models import Token
from rest_framework.pagination import LimitOffsetPagination
from rest_framework.permissions import AllowAny

from userapi.models import UserProfile, IMEI
from userapi.serializers import UserSerializer, WebUserSerializer, UserForgotPasswordSerializer
from userapi.tokens import account_activation_token
from .serializers import UserCreateSerializer, UserLoginSerializer
from rest_framework.generics import CreateAPIView, UpdateAPIView
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST
from rest_framework.views import APIView


# Create your views here.

class WebUserViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = UserProfile.objects.all()
    serializer_class = WebUserSerializer
    pagination_class = LimitOffsetPagination
    filter_backends = (filters.OrderingFilter, filters.SearchFilter,)
    search_fields = ('first_name', 'last_name', 'middle_name', 'email', 'phone')


class UserViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = UserProfile.objects.all()
    serializer_class = UserSerializer


class UserCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = UserCreateSerializer
    queryset = UserProfile.objects.all()


class UserLoginAPIView(APIView):
    permission_classes = [AllowAny]
    serializer_class = UserLoginSerializer

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            new_data = serializer.data
            return Response(new_data, status=HTTP_200_OK)
        return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)


class UserForgotPasswordAPIView(APIView):
    permission_classes = [AllowAny]
    serializer_class = UserForgotPasswordSerializer
    queryset = UserProfile.objects.all()

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserForgotPasswordSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            new_data = serializer.data
            return Response(new_data, status=HTTP_200_OK)
        return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)

class SetPasswordForm(forms.Form):
    """
    A form that lets a user change set their password without entering the old
    password
    """
    error_messages = {
        'password_mismatch': ("The two password fields didn't match."),
        }
    new_password1 = forms.CharField(label=("New password"),
                                    widget=forms.PasswordInput)
    new_password2 = forms.CharField(label=("New password confirmation"),
                                    widget=forms.PasswordInput)

    def clean_new_password2(self):
        password1 = self.cleaned_data.get('new_password1')
        password2 = self.cleaned_data.get('new_password2')
        if password1 and password2:
            if password1 != password2:
                raise forms.ValidationError(
                    self.error_messages['password_mismatch'],
                    code='password_mismatch',
                    )
        return password2


class UserSetPasswordView(FormView):
    template_name = "reset_password.html"
    success_url = "/"
    form_class = SetPasswordForm

    def post(self, request, uidb64=None, token=None, *arg, **kwargs):
        """
        View that checks the hash in a password reset link and presents a
        form for entering a new password.
        """
        form = self.form_class(request.POST)
        assert uidb64 is not None and token is not None  # checked by URLconf
        try:
            uid = urlsafe_base64_decode(uidb64)
            user = UserProfile.objects.get(pk=uid)
        except (TypeError, ValueError, OverflowError, UserProfile.DoesNotExist):
            user = None

        if user is not None and default_token_generator.check_token(user, token):
            if form.is_valid():
                new_password= form.cleaned_data['new_password2']
                user.set_password(new_password)
                user.save()
                return self.form_valid(form)
            else:
                return self.form_invalid(form)
        else:
            return self.form_invalid(form)

def activate(request, uidb64, token):
    uid = force_text(urlsafe_base64_decode(uidb64))
    user = UserProfile.objects.get(id=uid)
    if user is not None and account_activation_token.check_token(user, token):
        user.is_active = True
        user.save()
        return render_to_response('Email Confirmation/index.html')
    else:
        return HttpResponse('Activation link is invalid!')


def imei_activate(request, uidb64, token):
    uid = force_text(urlsafe_base64_decode(uidb64))
    imei = IMEI.objects.get(id=uid)
    if imei is not None:
        imei.is_active = True
        imei.save()
        return render_to_response('Phone Confirmation/index.html')
    else:
        return HttpResponse('Activation link is invalid!')