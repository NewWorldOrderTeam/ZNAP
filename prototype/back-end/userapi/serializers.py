# coding=utf-8
from django.conf.urls import url
from django.contrib.auth import login
from django.contrib.auth.models import User
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import EmailMessage
from django.template.loader import render_to_string
from django.utils.encoding import force_bytes
from django.utils.http import urlsafe_base64_encode
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, EmailField, IntegerField, SerializerMethodField

from rateapi.models import Rate
from rateapi.serializers import WebRateSerializer
from userapi.models import UserProfile, IMEI
from userapi.tokens import account_activation_token
from znap.AES import encryption, decryption

from rest_framework.test import APIRequestFactory

factory = APIRequestFactory()
request = factory.get('/')


class WebUserSerializer(serializers.HyperlinkedModelSerializer):
    rates = SerializerMethodField()

    class Meta:
        model = UserProfile
        fields = ('url', 'id', 'first_name', 'last_name', 'middle_name', 'email', 'phone', 'rates')

    def get_rates(self, obj):
        r_qs = Rate.objects.filter(user_id=obj.id)
        rates = WebRateSerializer(r_qs, many=True, context={'request': request}).data
        return rates


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('url', 'id', 'first_name', 'last_name', 'middle_name', 'email', 'phone')

    def to_representation(self, instance):
        ret = super(UserSerializer, self).to_representation(instance)
        ret['first_name'] = encryption(ret['first_name'])
        ret['last_name'] = encryption(ret['last_name'])
        ret['middle_name'] = encryption(ret['middle_name'])
        ret['email'] = encryption(ret['email'])
        ret['phone'] = encryption(ret['phone'])
        return ret


class UserCreateSerializer(serializers.HyperlinkedModelSerializer):
    first_name = CharField()
    last_name = CharField()
    middle_name = CharField()
    email = CharField(label='Email')
    phone = CharField()
    imei = CharField()
    class Meta:
        model = UserProfile
        fields = ['id','first_name', 'last_name', 'middle_name', 'email', 'password', 'phone', 'imei',]
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        email = data['email']
        user_qs = UserProfile.objects.filter(email=email)
        if user_qs.exists():
            raise serializers.ValidationError("This user has already registered")
        return data

    def create(self, validated_data):
        first_name = decryption(validated_data['first_name'])
        last_name = decryption(validated_data['last_name'])
        middle_name = decryption(validated_data['middle_name'])
        email = decryption(validated_data['email'])
        phone = decryption(validated_data['phone'])
        password = decryption(validated_data['password'])
        user_obj = UserProfile(
            username = email,
            first_name = first_name,
            last_name = last_name,
            middle_name = middle_name,
            email = email,
            phone=phone,
            is_active = False
        )
        user_obj.set_password(password)
        user_obj.save()

        imei = decryption(validated_data['imei'])
        imei_obj = IMEI (
            user_id=user_obj.id,
            name=imei,
            is_active=True
        )
        imei_obj.save()

        mail_subject = 'Активація аккаунта - ЦНАП'
        user = urlsafe_base64_encode(force_bytes(user_obj.id))
        token = account_activation_token.make_token(user_obj)
        message =  u'Привіт, {} \nБудь ласка, перейди за посиланням, щоб підтвердити реєстрацію\nhttp://znap.pythonanywhere.com/activate/{}/{}/'.format(user_obj.first_name, user, token)
        to_email = email
        email = EmailMessage(
            mail_subject, message, to=[to_email]
        )
        email.send()
        return validated_data

class UserLoginSerializer(serializers.ModelSerializer):
    email = CharField(label= 'Email', write_only=True)
    imei = CharField(label='IMEI', write_only=True)
    class Meta:
        model = UserProfile
        fields = ['id', 'email', 'password', 'imei']
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        user_obj = None
        email = decryption(data.get("email", None))
        password = decryption(data['password'])
        imei = decryption(data['imei'])
        user = UserProfile.objects.filter(email = email)
        user = user.exclude(email__isnull = True).exclude(email__iexact = '')
        if user.exists() and user.count()==1:
            user_obj = user.first()
        else:
            raise serializers.ValidationError("This email is not valid")
        if user_obj:
            if not user_obj.check_password(password):
                raise serializers.ValidationError("Incorrect password")
            elif (user_obj.is_active!=True):
                raise serializers.ValidationError("This account is not activated")
            elif not IMEI.objects.filter(user_id=user_obj.id).filter(name=imei).exists():
                imei_obj = IMEI(
                    user_id=user_obj.id,
                    name=imei,
                    is_active=False
                )
                imei_obj.save()
                mail_subject = 'Активація пристрою - ЦНАП'
                user = urlsafe_base64_encode(force_bytes(imei_obj.id))
                token = account_activation_token.make_token(imei_obj)
                message = u'Привіт, {} \nБудь ласка, перейди за посиланням, щоб підтвердити пристрій\nhttp://znap.pythonanywhere.com/activate-gadget/{}/{}/'.format(
                    user_obj.first_name, user, token)
                to_email = email
                email = EmailMessage(
                    mail_subject, message, to=[to_email]
                )
                email.send()
                raise serializers.ValidationError("Login from not activated phone")
            elif not IMEI.objects.filter(user_id=user_obj.id).get(name=imei).is_active:
                imei_obj = IMEI.objects.filter(user_id=user_obj.id).get(name=imei)
                mail_subject = 'Активація пристрою - ЦНАП'
                user = urlsafe_base64_encode(force_bytes(imei_obj.id))
                token = account_activation_token.make_token(imei_obj)
                message = u'Привіт, {} \nБудь ласка, перейди за посиланням, щоб підтвердити пристрій\nhttp://znap.pythonanywhere.com/activate-gadget/{}/{}/'.format(
                    user_obj.first_name, user, token)
                to_email = email
                email = EmailMessage(
                    mail_subject, message, to=[to_email]
                )
                email.send()
                raise serializers.ValidationError("This IMEI is not activated")
            else:
                data['id'] = user_obj.id
        return data

