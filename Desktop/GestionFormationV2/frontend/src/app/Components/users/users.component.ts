import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { TokenStorageService } from '../../Sevices/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from '../../Sevices/user.service';
import { User } from '../../Models/user';
import { RoleService } from '../../Sevices/role.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users: any;
  roles: any;
  deleteUser: any;
  userEdit: any;

  constructor(
    private roleService: RoleService,
    private router: Router,
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.getUsers();
      this.getRoles();
    } else {
      this.router.navigate(['/login']).then(() => {
        window.location.reload();
      });
    }
  }
  public delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe(
      (response: any[]) => {
        this.roles = response;
        console.log(this.roles);
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  public getUsers(): void {
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
        console.log(this.users);
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  public OnDeleteUser(userId: number): void {
    this.userService.deleteUser(userId).subscribe(
      async (response: any) => {
        console.log(response);
        //this.delay(1000);
        this.toastr.success("Utilisateur supprimé avec succès!","Félicitations")
        await this.delay(1510);
        window.location.reload();
      },
      (error: HttpErrorResponse) => {
        this.toastr.error(error.message,"Une erreur s'est produite :(");
        console.error(error.message);
      }
    );
  }

  public OnEditUser(userEdit: User): void {
    const edition = {
      id: userEdit.code,
      login: userEdit.login,
      password: userEdit.password,
    };
    console.log(userEdit);
    this.userService.updateUser(edition, userEdit.code).subscribe(
      (response: any) => {
        console.log(response);
        this.delay(1000);
        if (userEdit.roles) {
          this.userService
            .clearRoles(userEdit.code)
            .subscribe((response: any) => {
              console.log(response);
              this.delay(1000);
              userEdit.roles.forEach(async (roleId: any) => {
                this.userService
                  .linkUserToRole(userEdit.code, roleId)
                  .subscribe((response: any) => {
                    console.log(response);
                  });
                  this.toastr.success("Utilisateur modifié avec succès!","Félicitations")
                  await this.delay(1510);
                window.location.reload();
              });
            });
        }
      },
      (error: HttpErrorResponse) => {
        this.toastr.error(error.message,"Une erreur s'est produite :(");
        console.error(error.message);
      }
    );
  }

  public onOpenModal(user: any, mode: string): void {
    const container = document.getElementById('participantContainer');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'delete') {
      this.deleteUser = user;
      button.setAttribute('data-target', '#deleteUserModal');
    }
    if (mode === 'edit') {
      this.userEdit = user;
      button.setAttribute('data-target', '#editUserModal');
    }
    container?.appendChild(button);
    button.click();
  }
}
