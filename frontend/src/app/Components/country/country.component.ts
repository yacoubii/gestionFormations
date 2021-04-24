import { Component, OnInit } from '@angular/core';
import { Country } from '../../Models/country';
import { CountryService } from '../../Sevices/country.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../Sevices/token-storage.service';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.css'],
})
export class CountryComponent implements OnInit {
  title = 'formation';
  public countries: Country[] = [];
  public editCountry!: Country;
  public deleteCountry: any;
  constructor(
    private countryService: CountryService,
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.getCountries();
    } else {
      this.router.navigate(['/login']).then(() => {
        window.location.reload();
      });
    }
  }

  public getCountries(): void {
    this.countryService.getCountries().subscribe(
      (Response: Country[]) => {
        this.countries = Response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddCountry(addForm: NgForm): void {
    document.getElementById('add-country-form')?.click();
    this.countryService.addCountry(addForm.value).subscribe(
      (response: Country) => {
        console.log(response);
        this.getCountries();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onUpdateCountry(countryId: any, country: Country): void {
    this.countryService.updateCountry(countryId, country).subscribe(
      (response: Country) => {
        console.log(response);
        this.getCountries();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteCountry(countryId: number): void {
    this.countryService.deleteCountry(countryId).subscribe(
      (response: void) => {
        console.log(response);
        this.getCountries();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onOpenModal(country: any, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addCountryModal');
    }
    if (mode === 'edit') {
      this.editCountry = country;
      button.setAttribute('data-target', '#updateCountryModal');
    }
    if (mode === 'delete') {
      this.deleteCountry = country;
      button.setAttribute('data-target', '#deleteCountryIdModal');
    }
    container?.appendChild(button);
    button.click();
  }
}
