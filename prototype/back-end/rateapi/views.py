from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.authtoken.models import Token

from rateapi.models import Rate, Dialog
from rest_framework.generics import CreateAPIView

from rateapi.serializers import RateSerializer, RateCreateSerializer, AddMessageSerializer


class RateViewSet(viewsets.ModelViewSet):
    queryset = Rate.objects.all()
    serializer_class = RateSerializer

class RateCreateAPIView(CreateAPIView):
    serializer_class = RateCreateSerializer
    queryset = Rate.objects.all()

class AddMessageAPIView(CreateAPIView):
    serializer_class = AddMessageSerializer
    queryset = Dialog.objects.all()
