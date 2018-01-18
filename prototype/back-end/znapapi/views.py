import requests
from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView

from znapapi.models import Znap, RegistrationToZnap
from znapapi.serializers import ZnapSerialezer, CreateRegistrationToZnapSerializer, RegistrationToZnapSerializer


class ZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Znap.objects.all()
    serializer_class = ZnapSerialezer

class QlogicCnapViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self,request, format = None):
        r = requests.get('http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/getServiceCenterList?organisationGuid={28c94bad-f024-4289-a986-f9d79c9d8102}')
        cnaps = r.json()
        cnaps_list = cnaps['d']

        json_cnap = []
        for cnap in cnaps_list:
            name = cnap['ServiceCenterName']
            service_id = cnap['ServiceCenterId']
            json_cnap.append({'name':name,
                              'service_id': service_id})
        return Response(json_cnap)

class RegistrationToZnapCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = CreateRegistrationToZnapSerializer
    queryset = RegistrationToZnap.objects.all()

class RegistrationToZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = RegistrationToZnap.objects.all()
    serializer_class = RegistrationToZnapSerializer