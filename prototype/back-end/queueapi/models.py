# coding=utf-8
from datetime import datetime, date

from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices


class infoAboutCnap(models.Model):
    namesForZnaps = Choices(
        ('Виговського7', 'Виговського 7'),
        ('Ратуша', 'Ратуша'),
    )
    #znapName = models.CharField(max_length=1, choices=namesForZnaps, default=namesForZnaps.Виговського7)

class servicesForCNAP(models.Model):
    namesForServices = Choices(
        ('ОтриматиПаспорт','Отримати Паспорт'),
        ('РеєстраціяБізнесу','Реєстрація Бізнесу'),
    )
    typeForServices = Choices(
        ('Post', 'Подати документи'),
        ('Get', 'Отримати результат'),
        ('Registrer','Записатись на прийом'),
    )
    #serviceName = models.CharField(max_length=1,choices=namesForServices,default=namesForServices.ОтриматиПаспорт)
    serviceType = models.CharField(max_length=1, choices=typeForServices,default=typeForServices.Post)


class cnapWithService(models.Model):
    nameOfZnap = models.ForeignKey(infoAboutCnap)
    service = models.ForeignKey(servicesForCNAP)
    dateOfRegistration = models.DateField()
    status = models.BooleanField()

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithService)
    user = models.ForeignKey(User)
    time = models.DateTimeField()