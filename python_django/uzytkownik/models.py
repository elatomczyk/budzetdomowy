from datetime import date, datetime, timedelta
from django.contrib.auth.models import User
from django.db import models

class Kategorie(models.Model):
    nazwa = models.CharField(verbose_name='Nazwa kategorii', max_length=20)  
        
    class Meta:
        verbose_name = "Kategoria"
        verbose_name_plural = "Kategorie"    
    def __unicode__(self):
        return self.nazwa
    
class Rachunki(models.Model):
    tytul = models.CharField(verbose_name='Tytul rachunku', max_length=20)
    opis = models.CharField(verbose_name='Opis rachunku', max_length=200)
    data_zaplaty = models.DateField(verbose_name='Data zaplaty', default=date.today())
    cena = models.FloatField(verbose_name='Cena')
    www = models.URLField(verbose_name='Strona internetowa', blank=True)
    powiadomienie = models.BooleanField(default=False)
    id_k = models.ForeignKey(Kategorie, verbose_name='Kategoria', related_name='kategorie')
    id_u = models.ForeignKey(User, verbose_name='Uzytkownik')  
    
    class Meta:
        verbose_name = "Rachunek"
        verbose_name_plural = "Rachunki"
    def __unicode__(self):
        return self.tytul
 
class Dochody(models.Model):
    tytul = models.CharField(verbose_name='Tytul dochodu', max_length=20)
    opis = models.CharField(verbose_name='Opis dochodu', max_length=200)
    data_zaplaty = models.DateField(verbose_name='Data wplaty', default=date.today())
    cena = models.FloatField(verbose_name='Cena', default=0)
    id_u = models.ForeignKey(User, verbose_name='Uzytkownik')
    
    class Meta:
        verbose_name = "Dochod"
        verbose_name_plural = "Dochody"      
    def __unicode__(self):
        return self.tytul
