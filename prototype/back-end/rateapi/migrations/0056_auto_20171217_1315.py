# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-17 11:15
from __future__ import unicode_literals

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('rateapi', '0055_auto_20171217_1313'),
    ]

    operations = [
        migrations.AlterField(
            model_name='dialog',
            name='timeStamp',
            field=models.DateTimeField(default=datetime.datetime(2017, 12, 17, 13, 15, 36, 6136)),
        ),
    ]
