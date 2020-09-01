import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat.component';
import { SharedModule } from '../../shared/shared.module';
import { ChatRoutingModule } from './chat-routing.module';
import { MessagesComponent } from './components/messages/messages.component';

@NgModule({
  declarations: [
    ChatComponent,
    MessagesComponent
  ],
  imports: [
    SharedModule,
    ChatRoutingModule
  ]
})
export class ChatModule { }
