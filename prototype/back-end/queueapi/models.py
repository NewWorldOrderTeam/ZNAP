from datetime import date
from django.contrib.auth.models import User
from django.db import models
from django.forms import forms
from django.utils.dateformat import TimeFormat


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)

class servicesForCNAP(models.Model):
    serviceName = models.CharField(max_length=220)

class cnapWithService(models.Model):
    znap = models.ForeignKey(infoAboutCnap)
    service = models.ForeignKey(servicesForCNAP)
    dateForRegistration = models.DateField(default=date.today)
    timeForRegistration = models.TimeField(format('%H:%M'),null=True)

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithService)
    user = models.ForeignKey(User)
    time = models.DateTimeField()
