import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ForumService } from '../forum.service';
import { TopicDetail } from '../models/topic-detail';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-topic-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topic-detail.component.html',
  styleUrls: ['./topic-detail.component.scss']
})
export class TopicDetailComponent implements OnInit, OnDestroy {
  topicDetail: TopicDetail | null = null;
  isLoading: boolean = true;
  error: any = null;
  private routeSubscription?: Subscription;
  private topicDataSubscription?: Subscription;


  constructor(
    private route: ActivatedRoute,
    private forumService: ForumService
  ) {}

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const topicId = params.get('id');

      if (topicId) {
        this.fetchTopicDetails(+topicId);
      } else {
        this.isLoading = false;
        this.error = 'ID do tópico não fornecido na URL.';
      }
    });
  }

   ngOnDestroy(): void {
    this.routeSubscription?.unsubscribe();
    this.topicDataSubscription?.unsubscribe();
  }


  fetchTopicDetails(id: number): void {
    this.isLoading = true;
    this.error = null;
    this.topicDetail = null;

    this.topicDataSubscription = this.forumService.getTopic(id).subscribe(
      (data: TopicDetail | null) => {
        this.topicDetail = data;
        this.isLoading = false;
        if (!data) {
           this.error = 'Tópico não encontrado.';
        }
      },
      (error: any) => {
        console.error('Erro ao buscar detalhes do tópico:', error);
        this.error = 'Não foi possível carregar o tópico. Tente novamente mais tarde.';
        this.isLoading = false;
      }
    );
  }
}