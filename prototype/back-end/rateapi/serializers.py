from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, IntegerField, SerializerMethodField, BooleanField
from rest_framework_encrypted_lookup.serializers import EncryptedLookupModelSerializer
from rateapi.models import Rate
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
        return ret

class RateSerializer(serializers.ModelSerializer):
    description = CharField(required=False)
    quality = CharField(required=False)
    admin_id = CharField(required=False)
    class Meta:
        model = Rate
        fields = ('id', 'user_id', 'znap_id', 'admin_id', 'quality', 'description', 'is_closed')

    def to_representation(self, instance):
        ret = super(RateSerializer, self).to_representation(instance)
        ret['description'] = encryption(ret['description'])
        return ret

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

        return validated_data


