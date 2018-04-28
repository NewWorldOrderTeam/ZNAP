from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

from model_utils import Choices
import datetime
# Create your models here.

from adminapi.models import Admin
from znapapi.models import Znap


class Rate(models.Model):
    user = models.ForeignKey(User)
    admin = models.ForeignKey(Admin,default=None, null=True)
    znap = models.ForeignKey(Znap)
    quality = models.IntegerField()
    description = models.CharField(max_length=200)
    numOfTicket = models.IntegerField(null=True)
    is_closed = models.BooleanField()