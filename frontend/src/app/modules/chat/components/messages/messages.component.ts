import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {

  messages:Message[];

  constructor(private messageService: MessageService) { }

  ngOnInit(): void {
  //I think it might overflow the stack after very long time
    this.refresh();
  }

  sendMessage(username: string, msg:string){
    this.messageService.sendMessage(username,msg);
  }

  private refresh(){
    setTimeout(() => {
    this.messageService.getMessages().subscribe(data=>{this.messages=data});
    this.refresh();
    }, 500);
  }

}
