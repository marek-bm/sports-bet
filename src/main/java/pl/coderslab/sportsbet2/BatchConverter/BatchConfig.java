package pl.coderslab.sportsbet2.BatchConverter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import pl.coderslab.sportsbet2.model.Fixture;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    FixtureProcessor itemProcessor() {
        return new FixtureProcessor();
    }

    //based on https://www.youtube.com/watch?v=1XEX-u12i0A
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<FixtureDTO> fileItemReader,
                   ItemProcessor<FixtureDTO, Fixture> itemProcessor,
                   ItemWriter<Fixture> itemWriter) {

        Step step = stepBuilderFactory.get("file-load")
                .<FixtureDTO, Fixture>chunk(100)
                .reader(fileItemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("factory")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
        return job;
    }

    //implementation of itemReader
    @Bean
    public FlatFileItemReader<FixtureDTO> fileItemReader(@Value("${inputFile}") Resource resource) {
        FlatFileItemReader<FixtureDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-reader");
        flatFileItemReader.setLinesToSkip(1); //first row with header will be skipped
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<FixtureDTO> lineMapper() {
        DefaultLineMapper<FixtureDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"date", "ftag", "fthg", "ftr", "htag", "hthg", "htr", "match_status",
                "matchday", "away_team_id", "category_id", "home_team_id", "league_id", "season_id",
                "homeWinOdd", "drawOdd", "awayWinOdd", "goal_more_2_5", "goal_less_2_5"});

        BeanWrapperFieldSetMapper<FixtureDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FixtureDTO.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}

