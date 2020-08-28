import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './chat.component'
import { MessagesComponent } from './components/messages/messages.component';

const routes: Routes = [
    {path: '',
    component: ChatComponent,
    children: [
          {
            path: '',
            component: MessagesComponent
          }]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChatRoutingModule {}
