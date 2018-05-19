from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework_extensions.mixins import NestedViewSetMixin

from chatapi.models import Chat, Message
from chatapi.serializers import ChatSerializer, ChatCreateSerializer, AddMessageSerializer, MessageSerializer
from django_filters.rest_framework import DjangoFilterBackend

from znap.AES import encryption


class ChatViewSet(NestedViewSetMixin, viewsets.ModelViewSet ):
    permission_classes = [AllowAny]
    queryset = Chat.objects.all()
    serializer_class = ChatSerializer
    filter_backends = (DjangoFilterBackend,)
    filter_fields = ('is_closed', 'admin_id')

class ChatCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = ChatCreateSerializer
    queryset = Chat.objects.all()

class AddMessageAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = AddMessageSerializer
    queryset = Message.objects.all()

class MessageViewSet(NestedViewSetMixin, viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    serializer_class = MessageSerializer
    queryset = Message.objects.all()