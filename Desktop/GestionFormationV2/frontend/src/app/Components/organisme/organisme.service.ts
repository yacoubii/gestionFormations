import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Organisme} from 'src/app/Models/organisme';
import { OrganismeService } from 'src/app/Sevices/organisme.service';
@Component({
  selector: 'app-organisme',
  templateUrl: './organisme.component.html',
  styleUrls: ['./organisme.component.css']
})

export class OrganismeComponent implements OnInit {

  title = 'Excellent training';
  public organismes!: Organisme[];
  public editOrganisme!: Organisme; 
  public deleteOrganisme!: Organisme; 


  constructor(private organismeService:OrganismeService) { }

  ngOnInit(): void {
    this.getOrganismes();
  }
 
 
public getOrganismes(): void {
    this.organismeService.getOrganisme().subscribe(
      (response: Organisme[]) => {
        this.organismes = response;
        console.log(this.organismes);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    }

    public onAddOrganisme(addForm: NgForm):void{
      document.getElementById('add-organisme-form')?.click();
      this.organismeService.addOrganisme(addForm.value).subscribe(
        (response: Organisme) => {
          console.log(response);
          this.getOrganismes();
          addForm.reset();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );
    }
  
    public onDeleteOrganisme(organismeId: number): void {
      this.organismeService.deleteOrganisme(organismeId).subscribe(
        (response: void) => {
          console.log(response);
          this.getOrganismes();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  
    public onUpdateOrganisme(organisme: Organisme): void {
      this.organismeService.updateOrganisme(organisme,organisme.id).subscribe(
        (response: any) => {
          console.log(response);
          this.getOrganismes();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
 
    public searchOrganismes(key: string): void {
        console.log(key);
        const results: Organisme[] = [];
        for (const organisme of this.organismes) {
          if (organisme.libelle.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
            results.push(organisme);
          }
        }
        this.organismes = results;
        if (results.length === 0 || !key) {
          this.getOrganismes();
        }
      }

    public onOpenModal(organisme: any, mode: string): void {
      const container = document.getElementById('main-container');
      const button = document.createElement('button');
      button.type = 'button';
      button.style.display = 'none';
      button.setAttribute('data-toggle', 'modal');
      if (mode === 'add') {
        button.setAttribute('data-target', '#addOrganismeModal');
      }
      if (mode === 'edit') {
      this.editOrganisme = organisme;  
        button.setAttribute('data-target', '#updateOrganismeModal');
      }
    
      if (mode === 'delete') {
       this.deleteOrganisme = organisme;
       button.setAttribute('data-target', '#deleteOrganismeModal');
      }
      container?.appendChild(button);
      button.click();
    }
}
