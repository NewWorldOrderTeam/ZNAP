from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.fields import CharField, IntegerField, SerializerMethodField, BooleanField

from chatapi.models import Chat, Message

class ChatSerializer(serializers.ModelSerializer):
    admin_id = IntegerField(required=False)
    class Meta:
        model = Chat
        fields = ('id', 'user_id', 'admin_id', 'is_closed', 'timestamp')

class ChatCreateSerializer(serializers.HyperlinkedModelSerializer):
    user_id = IntegerField()
    class Meta:
        model = Chat
        fields = ('user_id', )

    def validate(self, data):
        user_id = data['user_id']
        user_qs = User.objects.filter(id=user_id)
        if not user_qs.exists():
            raise serializers.ValidationError('This user has not already registered')
        return data

    def create(self, validated_data):
        user_id = validated_data['user_id']
        chat_obj = Chat(
            user_id=user_id,
        )
        chat_obj.save()

        return validated_data

class MessageSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Message
        fields = ('message', 'timestamp', 'is_admin', 'is_read')


class AddMessageSerializer(serializers.HyperlinkedModelSerializer):
    chat_id = IntegerField()
    message = CharField(allow_blank=False)
    is_admin = BooleanField()
    class Meta:
        model = Message
        fields = ('chat_id', 'message', 'is_admin')

    def validate(self, data):
        chat_id = data['chat_id']
        chat_qs = Chat.objects.filter(id=chat_id)
        if not chat_qs.exists():
            raise serializers.ValidationError("This dialog has not already created")
        return data

    def create(self, validated_data):
        chat_id= validated_data['chat_id']
        message = validated_data['message']
        is_admin = validated_data['is_admin']
        message_obj = Message(
            chat_id=chat_id,
            message=message,
            is_admin=is_admin,
            is_read=False
        )
        message_obj.save()

        return validated_data

