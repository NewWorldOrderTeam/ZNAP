from __future__ import unicode_literals
from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices
import datetime

# Create your models here

class UserProfile(User):
    phone = models.CharField(max_length=15)
    middle_name = models.CharField(max_length=50)




