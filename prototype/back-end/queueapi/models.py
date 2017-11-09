from django.contrib.auth.models import User
from django.db import models
import userapi.models


class infoAboutCnap(models.Model):
    name = models.CharField(max_length=220)
    adress = models.CharField(max_length=250)

class typeOfCnap(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=220)

class cnapWithType(models.Model):
    znap_id = models.ForeignKey(infoAboutCnap)
    type_id = models.ForeignKey(typeOfCnap)
    peopleCounter = models.IntegerField()

class historyOfRecords(models.Model):
    history = models.ForeignKey(cnapWithType)
    user = models.ForeignKey(User)
    time = models.DateTimeField()
