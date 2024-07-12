/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Table(name = "dimrepetition")
@Entity
public class RepetitionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "period_start")
    private MeasureModel periodStart;

    @ManyToOne
    @JoinColumn(name = "period_duration")
    private MeasureModel periodDuration;

    @ManyToOne
    @JoinColumn(name = "event_frequency")
    private MeasureModel eventFrequency;

    @ManyToOne
    @JoinColumn(name = "event_separation")
    private MeasureModel eventSeparation;

    @ManyToOne
    @JoinColumn(name = "event_duration")
    private MeasureModel eventDuration;


    public RepetitionModel() {}

    public RepetitionModel(cdx.opencdx.grpc.data.Repetition repetition, ANFRepo anfRepo) {
        this.periodStart = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodStart()));
        this.periodDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodDuration()));
        this.eventFrequency = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventFrequency()));
        this.eventSeparation = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventSeparation()));
        this.eventDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventDuration()));
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasureModel getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(MeasureModel periodStart) {
        this.periodStart = periodStart;
    }

    public MeasureModel getPeriodDuration() {
        return periodDuration;
    }

    public void setPeriodDuration(MeasureModel periodDuration) {
        this.periodDuration = periodDuration;
    }


    public MeasureModel getEventFrequency() {
        return eventFrequency;
    }

    public void setEventFrequency(MeasureModel eventFrequency) {
        this.eventFrequency = eventFrequency;
    }


    public MeasureModel getEventSeparation() {
        return eventSeparation;
    }

    public void setEventSeparation(MeasureModel eventSeparation) {
        this.eventSeparation = eventSeparation;
    }


    public MeasureModel getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(MeasureModel eventDuration) {
        this.eventDuration = eventDuration;
    }
}
