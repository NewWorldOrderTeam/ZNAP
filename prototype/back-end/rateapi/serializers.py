from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, IntegerField

from rateapi.models import Rate, Dialog
from znapapi.models import Znap


class RateSerializer(serializers.ModelSerializer):
    description = CharField(required=False)
    quality = CharField(required=False)
    admin_id = CharField(required=False)
    class Meta:
        model = Rate
        fields = ('id', 'user_id', 'znap_id', 'admin_id', 'quality', 'description', 'is_closed')

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
    znap_id = IntegerField()
    class Meta:
        model = Rate
        fields = ('user_id', 'znap_id', 'description','quality')

    def validate(self, data):
        user_id = data['user_id']
        user_qs = User.objects.filter(id=user_id)
        if not user_qs.exists():
            raise serializers.ValidationError("This user has not already registered")
        return data

    def create(self, validated_data):
        user_id = validated_data['user_id']
        znap_id = validated_data['znap_id']
        description = validated_data['description']
        quality=validated_data['quality']
        rate_obj = Rate(
            user_id=user_id,
            znap_id = znap_id,
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


class AddMessageSerializer(serializers.HyperlinkedModelSerializer):
    dialog_id = IntegerField()
    message = CharField(allow_blank=True)
    class Meta:
        model = Dialog
        fields = ('dialog_id', 'message')

    def validate(self, data):
        dialog_id = data['dialog_id']
        dialog_qs = Rate.objects.filter(id=dialog_id)
        if not dialog_qs.exists():
            raise serializers.ValidationError("This dialog has not already created")
        return data

    def create(self, validated_data):
        dialog_id= validated_data['dialog_id']
        message = validated_data['message']
        dialog_obj = Dialog(
            dialog_id=dialog_id,
            message=message
        )
        dialog_obj.save()

        return validated_data


