from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, SerializerMethodField, IntegerField
from rest_framework.test import APIRequestFactory

from rateapi.models import Rate, Dialog

factory = APIRequestFactory()
request = factory.get('/')

class RateSerializer(serializers.ModelSerializer):
    dialog = SerializerMethodField()
    class Meta:
        model = Rate
        fields = ('user_id', 'admin_id', 'quality', 'description', 'is_closed', 'dialog' )

    def get_dialog(self, obj):
        d_qs = Dialog.objects.filter(dialog_id=obj.id)
        dialog = DialogSerializer(d_qs, many=True, context={'request': request}).data
        return dialog

class ChoicesField(serializers.Field):
    def __init__(self, choices, **kwargs):
        self._choices = choices
        super(ChoicesField, self).__init__(**kwargs)

    def to_representation(self, obj):
        return self._choices[obj]

    def to_internal_value(self, data):
        return getattr(self._choices, data)


class RateCreateSerializer(serializers.HyperlinkedModelSerializer):
    user_id = IntegerField()
    description = CharField(allow_blank=True)
    quality = IntegerField()
    class Meta:
        model = Rate
        fields = ('user_id', 'description','quality')

    def validate(self, data):
        user_id = data['user_id']
        user_qs = User.objects.filter(id=user_id)
        if not user_qs.exists():
            raise serializers.ValidationError("This user has not already registered")
        return data

    def create(self, validated_data):
        user_id = validated_data['user_id']
        description = validated_data['description']
        quality=validated_data['quality']
        rate_obj = Rate(
            user_id=user_id,
            quality=quality,
            description=description,
            is_closed=False
        )
        rate_obj.save()

        chat_obj = Dialog(
            dialog_id=rate_obj.id,
            message=description
        )

        chat_obj.save()
        return validated_data


class DialogSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Dialog
        fields = ('message', 'timeStamp')