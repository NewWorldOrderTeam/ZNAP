# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2017-12-20 00:34
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('znapapi', '0002_registrationtoznap'),
    ]

    operations = [
        migrations.AlterField(
            model_name='registrationtoznap',
            name='date',
            field=models.CharField(max_length=124),
        ),
        migrations.AlterField(
            model_name='registrationtoznap',
            name='time',
            field=models.CharField(max_length=124),
        ),
    ]
