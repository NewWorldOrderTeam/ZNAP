import base64

from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, IntegerField, SerializerMethodField, BooleanField
from rest_framework_encrypted_lookup.serializers import EncryptedLookupModelSerializer

from rateapi.models import Rate, Dialog
from userapi.models import UserProfile
from znap.AES import encryption, decryption
from znapapi.models import Znap

from rest_framework.test import APIRequestFactory

class WebRateSerializer(serializers.ModelSerializer):

    description = CharField(required=False)
    quality = CharField(required=False)
    admin_id = CharField(required=False)
    class Meta:
        model = Rate
        fields = ('id', 'znap_id', 'user_id', 'admin_id', 'quality', 'description', 'is_closed')

    def to_representation(self, instance):
        ret = super(WebRateSerializer, self).to_representation(instance)
        try:
            ret['first_name'] = UserProfile.objects.filter(id=instance.user_id)[0].first_name
            ret['last_name'] = UserProfile.objects.filter(id=instance.user_id)[0].last_name
        except:
            pass
        ret['timestamp'] = Dialog.objects.filter(dialog_id=instance.id)[0].timeStamp
        return ret

class RateSerializer(serializers.ModelSerializer):
    description = CharField(required=False)
    quality = CharField(required=False)
    admin_id = CharField(required=False)
    class Meta:
        model = Rate
        fields = ('id', 'user_id', 'znap_id', 'admin_id', 'quality', 'description', 'is_closed', 'user')

    def to_representation(self, instance):
        ret = super(RateSerializer, self).to_representation(instance)
        ret['description'] = encryption(ret['description'])
        return ret


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
        description = decryption(validated_data['description'])
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
            message=description,
            is_admin=False
        )

        chat_obj.save()
        return validated_data


class DialogSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Dialog
        fields = ('message', 'timeStamp', 'is_admin')


class AddMessageSerializer(serializers.HyperlinkedModelSerializer):
    dialog_id = IntegerField()
    message = CharField(allow_blank=True)
    is_admin = BooleanField()
    class Meta:
        model = Dialog
        fields = ('dialog_id', 'message', 'is_admin')

    def validate(self, data):
        dialog_id = data['dialog_id']
        dialog_qs = Rate.objects.filter(id=dialog_id)
        if not dialog_qs.exists():
            raise serializers.ValidationError("This dialog has not already created")
        return data

    def create(self, validated_data):
        dialog_id= validated_data['dialog_id']
        message = validated_data['message']
        is_admin = validated_data['is_admin']
        dialog_obj = Dialog(
            dialog_id=dialog_id,
            message=message,
            is_admin=is_admin
        )
        dialog_obj.save()

        return validated_data


