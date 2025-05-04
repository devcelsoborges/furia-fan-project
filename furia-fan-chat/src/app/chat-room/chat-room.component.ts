import { Component, OnInit, AfterViewChecked, ElementRef, ViewChild } from '@angular/core';
import { ChatService } from '../chat.service';
import { Message } from '../message/message.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-chat-room',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.scss']
})
export class ChatRoomComponent implements OnInit, AfterViewChecked {
  messages: Message[] = [];
  newMessage: string = '';
  selectedFile: File | null = null;

  @ViewChild('scrollContainer') private scrollContainer!: ElementRef;

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }

  loadMessages(): void {
    this.chatService.getMessages().subscribe((data: Message[]) => {
      this.messages = data;
    });
  }

  sendMessage(): void {
    if (this.newMessage.trim()) {
      this.chatService.sendMessage(this.newMessage).subscribe(() => {
        this.newMessage = '';
        this.loadMessages();
      });
    }
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  uploadFile(): void {
    if (this.selectedFile) {
      this.chatService.uploadDocument(this.selectedFile).subscribe(
        (response) => {
          alert(response);
          this.selectedFile = null;
          this.loadMessages();
        },
        (error) => {
          alert("Erro ao enviar documento: " + error.message);
        }
      );
    } else {
      alert("Selecione um arquivo primeiro.");
    }
  }

  private scrollToBottom(): void {
    try {
      this.scrollContainer.nativeElement.scrollTop = this.scrollContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error('Erro ao aplicar scroll automático:', err);
    }
  }
}
