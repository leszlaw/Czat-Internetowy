import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './modules/material.module'


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  exports:[
    HttpClientModule,
    CommonModule,
    RouterModule,
    FormsModule,
    MaterialModule,
  ]
})
export class SharedModule { }
