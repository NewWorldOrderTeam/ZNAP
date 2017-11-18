from datetime import datetime, date

from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)

class servicesForCNAP(models.Model):
    namesForServices = Choices(
        ('Виговського7','Виговського 7'),
        ('Ратуша','Ратуша'),
    )
    typeForServices = Choices(
        ('Post', 'Подати документи'),
        ('Get', 'Отримати результат'),
        ('Registrer','Записатись на прийом'),
    )
    serviceName = models.CharField(max_length=1,choices=namesForServices,default=namesForServices.Виговського7)
    serviceType = models.CharField(max_length=1, choices=typeForServices,default=typeForServices.Post)


class cnapWithService(models.Model):
    nameOfZnap = models.ForeignKey(infoAboutCnap)
    service = models.ForeignKey(servicesForCNAP,null=True)
    dateForRegistration = models.DateField(default=date.today)
    timeForRegistration = models.TimeField(format('%H:%M'),null=True)
    date = models.DateTimeField(default=datetime.now(), blank=True)
    status = models.BooleanField()

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithService)
    user = models.ForeignKey(User)
    time = models.DateTimeField()