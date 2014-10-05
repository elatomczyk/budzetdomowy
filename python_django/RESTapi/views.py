from django.http.response import Http404
from rest_framework.response import Response
from rest_framework.views import APIView
from django.contrib.auth.models import User
from RESTapi.serializers import DochodySerializer, RachunkiSerializer, KategorieSerializer
from uzytkownik.models import Dochody, Rachunki,Kategorie

class KategorieLista(APIView):
    def get(self, request, format=None):
        lista = Kategorie.objects.all()
        serializer_kategoria = KategorieSerializer(lista)
        return Response(serializer_kategoria.data)
    
class DodawanieRachunku(APIView):
    def get(self, request,tytull,opiss,cenaa,powiadomieniee,id_kk,id_uu, format=None):
        data = {'tag': 0} 
        kat = Kategorie.objects.get(nazwa = id_kk)
        user= User.objects.get(username = id_uu)
        
        Rachunki.objects.create(tytul = tytull,opis = opiss,cena = cenaa,powiadomienie =powiadomieniee ,id_k = kat,id_u = user)
        data['tag'] = "Dodano"
        return Response(data)
    
class DodawanieDochodu(APIView):
    def get(self, request,tytull,opiss,cenaa,id_uu,format=None):
        data = {'tag': 0} 
        user= User.objects.get(username = id_uu)
        Dochody.objects.create(tytul = tytull,opis = opiss,cena = cenaa,id_u = user)
        data['tag'] = "Dodano"
        return Response(data)

class UsuwanieRachunki(APIView):
    def get(self, request,pk, format=None):
        data = {'tag': 0} 
        do_usuniecia = Rachunki.objects.filter(id = pk)
        do_usuniecia.delete()
        data['tag'] = "Usuniete"
        return Response(data)
    
class UsuwanieDochody(APIView):
    def get(self, request,pk, format=None):
        data = {'tag': 0} 
        do_usuniecia = Dochody.objects.filter(id = pk)
        do_usuniecia.delete()
        data['tag'] = "Usuniete"
        return Response(data)

class Login(APIView):
    def get(self, request, nazwa, haslo, format=None):
        
        data = {'tag': 0} 
       
        user = User.objects.get(username=nazwa)
        
        if user.check_password(haslo):
            data['tag'] = "Logged"
            return Response(data)
        else:
            data['tag'] = "Nie"
            return Response(data)


class DochodyLista(APIView):
    def get(self, request, nazwa, format=None):
        uzytkownik = User.objects.get(username=nazwa)
        if uzytkownik:
            lista = Dochody.objects.filter(id_u = uzytkownik)
            serializer_dochod = DochodySerializer(lista)
            return Response(serializer_dochod.data)

class DochodyDetail(APIView):
    def get_object(self, pk):
        try:
            return Dochody.objects.get(id=pk)
        except Dochody.DoesNotExist:
            raise Http404
    def get(self, request, pk, format=None):
        lista = self.get_object(pk)
        serializer_dochody = DochodySerializer(lista)
        return Response(serializer_dochody.data)
   
class RachunkiLista(APIView):
    def get(self, request, nazwa, format=None):
        uzytkownik = User.objects.get(username=nazwa)
        if uzytkownik:
            lista = Rachunki.objects.filter(id_u = uzytkownik)
            serializer_rachunek = RachunkiSerializer(lista)
            return Response(serializer_rachunek.data)     
    
class RachunkiDetail(APIView):
    def get_object(self, pk):
        try:
            return Rachunki.objects.get(id=pk)
        except Rachunki.DoesNotExist:
            raise Http404
    def get(self, request, pk, format=None):
        lista = self.get_object(pk)
        serializer_rachunki = RachunkiSerializer(lista)
        return Response(serializer_rachunki.data)
    
