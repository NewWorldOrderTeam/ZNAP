from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.fields import CharField, IntegerField, SerializerMethodField, BooleanField

from chatapi.models import Chat, Message

class ChatSerializer(serializers.ModelSerializer):
    admin_id = IntegerField(required=False)
    class Meta:
        model = Chat
        fields = ('id', 'user_id', 'admin_id', 'is_closed', 'timestamp')

    def to_representation(self, instance):
        ret = super(ChatSerializer, self).to_representation(instance)
        try:
            last_obj = Message.objects.filter(chat_id=instance.id).last()
            ret['last_message'] = last_obj.message
            ret['is_admin'] = last_obj.is_admin
            ret['last_timestamp'] = last_obj.timestamp
        except:
            ret['last_message'] = ''
            ret['is_admin'] = ''
            ret['last_timestamp'] = ''
        return ret

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
            raise serializers.ValidationError('This dialog has not already created')
        else:
            chat_obj = chat_qs.first()
            is_admin = data['is_admin']
            if is_admin and chat_obj.admin_id==None:
                raise serializers.ValidationError('Incorrect field is_admin')
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

