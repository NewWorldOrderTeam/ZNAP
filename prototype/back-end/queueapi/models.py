from django.contrib.auth.models import User
from django.db import models


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)

class typeOfCnap(models.Model):
    name = models.CharField(max_length=220)

class cnapWithType(models.Model):
    znap = models.ForeignKey(infoAboutCnap)
    type = models.ForeignKey(typeOfCnap)
    peopleCounter = models.IntegerField()

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithType)
    user = models.ForeignKey(User)
    time = models.DateTimeField()
