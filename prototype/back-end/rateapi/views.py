from django.shortcuts import render

# Create your views here.
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import viewsets
from rest_framework.authtoken.models import Token
from rest_framework.permissions import AllowAny
from rest_framework_encrypted_lookup.views import EncryptedLookupGenericViewSet

from rateapi.models import Rate
from rest_framework.generics import CreateAPIView

from rateapi.serializers import RateSerializer, RateCreateSerializer, \
    WebRateSerializer
from rest_framework_extensions.mixins import NestedViewSetMixin

class WebRateViewSet(NestedViewSetMixin, viewsets.ModelViewSet ):
    permission_classes = [AllowAny]
    queryset = Rate.objects.all()
    serializer_class = WebRateSerializer
    filter_backends = (DjangoFilterBackend,)
    filter_fields = ('is_closed', 'admin_id')

class RateViewSet(NestedViewSetMixin, viewsets.ModelViewSet ):
    permission_classes = [AllowAny]
    queryset = Rate.objects.all()
    serializer_class = RateSerializer
    filter_backends = (DjangoFilterBackend,)
    filter_fields = ('is_closed', 'admin_id')

class RateCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = RateCreateSerializer
    queryset = Rate.objects.all()