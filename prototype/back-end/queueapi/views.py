from rest_framework import viewsets

from rest_framework.permissions import AllowAny
from rest_framework.generics import CreateAPIView

from queueapi.models import cnapWithService
from queueapi.serializers import QueueSerializer, QueueCreateSerializer


class QueueViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = cnapWithService.objects.all()
    serializer_class = QueueSerializer

class QueueCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = QueueCreateSerializer
    queryset = cnapWithService.objects.all()
