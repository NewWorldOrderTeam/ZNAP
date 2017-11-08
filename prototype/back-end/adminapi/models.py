from __future__ import unicode_literals

from django.db import models

# Create your models here.
from model_utils import Choices


class Admin(models.Model):
    email = models.EmailField()
    password = models.CharField(max_length=50)
    def __str__(self):
        return str(self.email)

class Queue(models.Model):
    nameOFCNAP = Choices(
        ('1', 'first')
    )
    adressOFCNAP = Choices(
        ('1', 'NULP')
    )
    nameOfDepartment = models.CharField(max_length=1, choices=nameOFCNAP)
    location = models.CharField(max_length=1, choices=adressOFCNAP)