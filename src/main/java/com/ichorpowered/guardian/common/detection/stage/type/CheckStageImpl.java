/*
 * MIT License
 *
 * Copyright (c) 2017 Connor Hartley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ichorpowered.guardian.common.detection.stage.type;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.ichorpowered.guardian.api.detection.check.Check;
import com.ichorpowered.guardian.api.detection.stage.Stage;
import com.ichorpowered.guardian.api.detection.stage.StageProcess;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class CheckStageImpl implements Stage<Check<?>> {

    private final Map<Class<? extends Check<?>>, Check<?>> processes = Maps.newHashMap();
    private final int maximum;

    @Inject
    public CheckStageImpl(final List<StageProcess> processes,
                          final int maximum) {
        processes.forEach(stageProcess -> this.processes.put((Class<Check<?>>) stageProcess.getClass(), (Check<?>) stageProcess));

        this.maximum = maximum;
    }

    @Override
    public @NonNull Optional<Check<?>> getProcess(@NonNull Class<? extends Check<?>> stageProcessType) {
        return Optional.ofNullable(this.processes.get(stageProcessType));
    }

    @Override
    public @NonNull Predicate<Check<?>> getFilter() {
        return null;
    }

    @Override
    public @NonNull int getMaximum() {
        return this.maximum;
    }

    @Override
    public @NonNull int getSize() {
        return this.processes.size();
    }

    @Override
    public @NonNull Iterator<Check<?>> iterator() {
        return this.processes.values().iterator();
    }

}
