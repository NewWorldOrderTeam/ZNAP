from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from rest_framework.permissions import AllowAny
from rest_framework.views import APIView

from adminapi.models import Admin
from adminapi.serializers import AdminLoginSerializer, AdminSerializer

from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST


class AdminLoginAPIView(APIView):
    permission_classes = [AllowAny]
    serializer_class = AdminLoginSerializer

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = AdminLoginSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            new_data = serializer.data
            return Response(new_data, status=HTTP_200_OK)
        return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)

class AdminViewSet(viewsets.ModelViewSet):
    queryset = Admin.objects.all()
    serializer_class = AdminSerializer
