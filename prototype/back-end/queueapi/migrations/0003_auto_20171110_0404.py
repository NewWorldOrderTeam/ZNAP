# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2017-11-10 02:04
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('queueapi', '0002_auto_20171110_0403'),
    ]

    operations = [
        migrations.RenameField(
            model_name='cnapwithtype',
            old_name='type_id',
            new_name='type',
        ),
        migrations.RenameField(
            model_name='cnapwithtype',
            old_name='znap_id',
            new_name='znap',
        ),
    ]
