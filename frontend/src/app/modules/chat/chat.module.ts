import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat.component';
import { SharedModule } from '../../shared/shared.module';
import { ChatRoutingModule } from './chat-routing.module';


@NgModule({
  declarations: [
    ChatComponent
  ],
  imports: [
    SharedModule,
    ChatRoutingModule
  ]
})
export class ChatModule { }
