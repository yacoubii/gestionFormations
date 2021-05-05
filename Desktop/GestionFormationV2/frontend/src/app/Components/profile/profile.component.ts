import { Component, OnInit } from '@angular/core';
import { Profile } from '../../Models/profile';
import { ProfileService } from '../../Sevices/profile.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../Sevices/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  title = 'formation';
  public profiles: Profile[] = [];
  public editProfile!: Profile;
  public deleteProfile: any;
  constructor(
    private profileService: ProfileService,
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.getProfiles();
    } else {
      this.router.navigate(['/login']).then(() => {
        window.location.reload();
      });
    }
  }
  public getProfiles(): void {
    this.profileService.getProfiles().subscribe(
      (Response: Profile[]) => {
        this.profiles = Response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddProfile(addForm: NgForm): void {
    document.getElementById('add-profile-form')?.click();
    this.profileService.addProfile(addForm.value).subscribe(
      (response: Profile) => {
        console.log(response);
        window.location.reload();
        this.getProfiles();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onUpdateProfile(profileId: any, profile: Profile): void {
    this.profileService.updateProfile(profileId, profile).subscribe(
      (response: Profile) => {
        console.log(response);
        this.getProfiles();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteProfile(profileId: number): void {
    this.profileService.deleteProfile(profileId).subscribe(
      (response: void) => {
        console.log(response);
        window.location.reload();
        this.getProfiles();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onOpenModal(profile: any, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addProfileModal');
    }
    if (mode === 'edit') {
      this.editProfile = profile;
      button.setAttribute('data-target', '#updateProfileModal');
    }
    if (mode === 'delete') {
      this.deleteProfile = profile;
      button.setAttribute('data-target', '#deleteProfileModal');
    }
    container?.appendChild(button);
    button.click();
  }
}
