from django import forms
from models import Rachunki,Dochody
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm

class RachunkiForm(forms.ModelForm):
    
    class Meta:
        model = Rachunki
        fields = ('tytul','opis','www','powiadomienie','id_k','data_zaplaty','cena') 

class DochodyForm(forms.ModelForm):
    
    class Meta:
        model = Dochody
        fields = ('tytul','opis','data_zaplaty','cena')

class MyRegistrationForm(UserCreationForm):
    email = forms.EmailField(required=True)
    
    class Meta:
        model = User
        fields = ('username', 'email', 'password1', 'password2')
        
    def save(self, commit=True):
        user = super(MyRegistrationForm, self).save(commit=False)
        user.email = self.cleaned_data['email']       
        if commit:
            user.save()         
        return user
