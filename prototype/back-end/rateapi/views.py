from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.authtoken.models import Token
from rest_framework.permissions import AllowAny

from rateapi.models import Rate, Dialog
from rest_framework.generics import CreateAPIView

from rateapi.serializers import RateSerializer, RateCreateSerializer, AddMessageSerializer, DialogSerializer
from rest_framework_extensions.mixins import NestedViewSetMixin


class RateViewSet(NestedViewSetMixin, viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Rate.objects.all()
    serializer_class = RateSerializer

class RateCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = RateCreateSerializer
    queryset = Rate.objects.all()

class AddMessageAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = AddMessageSerializer
    queryset = Dialog.objects.all()

class DialogViewSet(NestedViewSetMixin, viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    serializer_class = DialogSerializer
    queryset = Dialog.objects.all()