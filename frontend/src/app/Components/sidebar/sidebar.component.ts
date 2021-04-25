import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent implements OnInit {


  activeClass:string;
  constructor() {
    this.activeClass='active'
   }

  ngOnInit(): void {
  }

  public clickFun(event:any){
    var list: any = document.getElementsByClassName('item');
    for (let item of list) {
    item.removeAttribute('id');
    }
    event.target.setAttribute('id','active');
  }

  
  public clickReset(){
    var list: any = document.getElementsByClassName('item');
    for (let item of list) {
    item.removeAttribute('id');
    }
  }
}
