# coding=utf-8
import json
import urllib

import pdfkit
from django.template.loader import render_to_string
from django.utils.html import strip_tags
from rest_framework.response import Response
from django.contrib.auth.models import User
from django.core.mail import EmailMessage
from rest_framework import serializers
from rest_framework.fields import CharField, IntegerField, DateField, TimeField, BooleanField

from userapi.models import UserProfile
from znap.settings import organisationGuid
from znapapi.models import Znap, RegistrationToZnap


class ZnapSerialezer(serializers.ModelSerializer):
    name = CharField(read_only=True)

    class Meta:
        model = Znap
        fields = ('id', 'name')

class RegistrationToZnapSerializer(serializers.ModelSerializer):
    class Meta:
        model = RegistrationToZnap
        fields = ('id', 'user_id', 'date', 'time', 'html')

class QlogicFinishRegistrationToZnapSerializer(serializers.Serializer):
    user_id = IntegerField(write_only=True)
    cnap_id = IntegerField(write_only=True)
    service_id = IntegerField(write_only=True)
    day = CharField(write_only=True)
    hour = CharField(write_only=True)
    html = CharField(read_only=True)

    def create(self, validated_data):
        user_id = validated_data['user_id']
        cnap_id = validated_data['cnap_id']
        service_id = validated_data['service_id']
        day = validated_data['day']
        hour = validated_data['hour']
        try:
            user_obj = UserProfile.objects.get(id=user_id)
        except:
            raise serializers.ValidationError('This user has not already registered')
        first_name = user_obj.first_name
        midle_name = user_obj.middle_name
        last_name = user_obj.last_name
        email = user_obj.email
        phone = user_obj.phone
        try:
            url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/RegCustomerEx?organisationGuid=' + organisationGuid + \
                  '&serviceCenterId=' + str(cnap_id) + '&serviceId=' + str(
                service_id) + '&LangId=1&phone=' + phone + '&email=' + email + \
                  '&name=' + unicode(last_name) + ' ' + unicode(first_name) + ' ' + unicode(midle_name) + '&date=' + day + ' ' + hour
            url = url.encode('utf8')
            r = urllib.urlopen(url).read()
            cnap_registration = json.loads(r)['d']
            order_id = cnap_registration['CustOrderGuid']
            url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/getReceipt?organisationGuid=' + organisationGuid + '&serviceCenterId=' + str(cnap_id) + '&orderGuid={' + order_id + '}'
            r = urllib.urlopen(url).read()
            registration_check = json.loads(r)['d']

            registration_check = registration_check.replace('windows-1251', 'utf-8')
            registration_check = registration_check.replace('&nbsp', '  ')

            validated_data['html']=registration_check
            mail_subject = "Реєстрація у ЦНАП"
            email = EmailMessage(
                mail_subject, registration_check, to=[email]
            )
            email.content_subtype ='html'
            email.send()

            registration_to_cnap = RegistrationToZnap (
                user_id=user_id,
                znap=cnap_id,
                service=service_id,
                date=day,
                time=hour,
                html=registration_check
            )
            registration_to_cnap.save()

            return validated_data
        except:
            raise serializers.ValidationError('Bad registration to CNAP')
