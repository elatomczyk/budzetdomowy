from uzytkownik.models import Dochody, Rachunki, Kategorie
from rest_framework import serializers

class KategorieSerializer(serializers.ModelSerializer):
    class Meta:
        model = Kategorie
        fields = ('id', 'nazwa')

class DochodySerializer(serializers.ModelSerializer):  
    class Meta:
        model = Dochody
        fields = ('id', 'tytul', 'opis', 'data_zaplaty', 'cena')
    
class RachunkiSerializer(serializers.ModelSerializer):
    kategorie_nazwa = serializers.RelatedField(source='id_k')
    class Meta:
        model = Rachunki
        fields = ('id', 'tytul', 'opis', 'data_zaplaty', 'cena', 'www', 'powiadomienie', 'kategorie_nazwa')
        