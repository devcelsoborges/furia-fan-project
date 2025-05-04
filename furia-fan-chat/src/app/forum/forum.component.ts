import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ForumService } from '../forum.service';
import { Topic } from '../models/topic.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forum',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, FormsModule],
  templateUrl: './forum.component.html',
  styleUrl: './forum.component.scss'
})
export class ForumComponent implements OnInit, OnDestroy {

  topics: Topic[] = [];
  private topicSubscription: Subscription | undefined;
  private createTopicSubscription: Subscription | undefined;

  posts = [
    { autor: 'Fã1', mensagem: 'FURIA jogou muito ontem! 💪', data: new Date() }
  ];

  stats = {
    members: '130,819',
    record: '1,492',
    recordDate: 'Outubro 4, 2023',
    newestMember: 'kadu_amorim',
    joinedAgo: '7 horas',
  };

  showNewTopicForm = false;
  newTopicAuthor = '';
  newTopicTitle = '';
  initialPostContent = '';

  constructor(private forumService: ForumService) {}

  ngOnInit(): void {
    this.carregarTopicos();
  }

  ngOnDestroy(): void {
    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
    }
    if (this.createTopicSubscription) {
        this.createTopicSubscription.unsubscribe();
    }
  }

  carregarTopicos(): void {
    this.topicSubscription = this.forumService.listarTopicos().subscribe({
      next: (topics: Topic[]) => {
        this.topics = topics;
      },
      error: (error: any) => {
        console.error('Erro ao carregar tópicos:', error);
      }
    });
  }

  iniciarNovoTopico(): void {
    this.showNewTopicForm = true;
    this.newTopicAuthor = '';
    this.newTopicTitle = '';
    this.initialPostContent = '';
  }

  submitNewTopic(): void {
    if (!this.newTopicAuthor || !this.newTopicTitle || !this.initialPostContent) {
      alert('Por favor, preencha todos os campos.');
      return;
    }

    const topicData = {
      titulo: this.newTopicTitle,
      descricao: this.initialPostContent,
      autor: this.newTopicAuthor
    };

    const postData = {
      autor: this.newTopicAuthor,
      mensagem: this.initialPostContent
    };

    this.createTopicSubscription = this.forumService.criarTopicoComPostInicial(topicData, postData).subscribe({
        next: (postCriado) => {
            console.log('Tópico e Post inicial criados com sucesso:', postCriado);
            this.showNewTopicForm = false;
            this.carregarTopicos();
        },
        error: (error: any) => {
            console.error('Erro ao criar tópico/post:', error);
        }
    });
  }

  cancelNewTopic(): void {
    this.showNewTopicForm = false;
  }
}