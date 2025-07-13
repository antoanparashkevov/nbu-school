package org.example.gradingcenter.web.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.ProgramSlot;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramViewModel {

    private Map<DayOfWeek, ProgramSlot> programs = new HashMap<>();

}
