from django.contrib.auth.models import User
from django.db import models


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)

class servicesForCNAP(models.Model):
    serviceName = models.CharField(max_length=220)

class cnapWithService(models.Model):
    znap = models.ForeignKey(infoAboutCnap)
    service = models.ForeignKey(servicesForCNAP)

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithService)
    user = models.ForeignKey(User)
    time = models.DateTimeField()
