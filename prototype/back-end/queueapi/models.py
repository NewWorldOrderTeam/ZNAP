from datetime import datetime, date

from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)
    date = models.DateTimeField(default=datetime.now(), blank=True)
    status = models.BooleanField()

class servicesForCNAP(models.Model):
    serviceName = models.CharField(max_length=220)
    CHOICES = Choices(
        ('Get', 'Отримати довідку'),
        ('Post', 'Надіслати довідку'),
    )

    serviceType = models.CharField(max_length=1, choices=CHOICES,default=CHOICES.Get)


class cnapWithService(models.Model):
    znap = models.ForeignKey(infoAboutCnap)
    service = models.ForeignKey(servicesForCNAP)
    dateForRegistration = models.DateField(default=date.today)
    timeForRegistration = models.TimeField(format('%H:%M'),null=True)

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithService)
    user = models.ForeignKey(User)
    time = models.DateTimeField()
