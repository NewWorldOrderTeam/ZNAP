from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import AllowAny

from znapapi.models import Znap, RegistrationToZnap
from znapapi.serializers import ZnapSerialezer, CreateRegistrationToZnapSerializer, RegistrationToZnapSerializer


class ZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Znap.objects.all()
    serializer_class = ZnapSerialezer

class RegistrationToZnapCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = CreateRegistrationToZnapSerializer
    queryset = RegistrationToZnap.objects.all()

class RegistrationToZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = RegistrationToZnap.objects.all()
    serializer_class = RegistrationToZnapSerializer