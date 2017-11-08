from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.authtoken.models import Token

from rateapi.models import Rate
from rest_framework.generics import CreateAPIView

from rateapi.serializers import RateSerializer, RateCreateSerializer


class RateViewSet(viewsets.ModelViewSet):
    queryset = Rate.objects.all()
    serializer_class = RateSerializer

class RateCreateAPIView(CreateAPIView):
    serializer_class = RateCreateSerializer
    queryset = Rate.objects.all()
