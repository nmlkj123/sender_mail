package amin.code.orders.batch.config;

import amin.code.orders.batch.mapper.OrderMapper;
import amin.code.orders.batch.processor.OrderItemProcessor;
import amin.code.orders.batch.writer.OrderWriter;
import amin.code.orders.entity.OrdersDTO;
import amin.code.orders.mailMarketing.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSendException;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Random;

@EnableBatchProcessing
@Slf4j
@Configuration
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    DataSource dataSource;

    @Autowired
    MailService mailService;

    private final String JOB_NAME = "emailSenderJob";
    private final String STEP_NAME = "emailSenderStep";

    Random random = new Random();
    int randomWithNextInt = random.nextInt();


    @Bean(name = "emailSenderJob")
    public Job emailSenderJob() {
        return this.jobBuilderFactory.get(JOB_NAME+randomWithNextInt)
                .start(emailSenderStep())
                .build();
    }

    @Bean
    public Step emailSenderStep() {


        return this.stepBuilderFactory
                .get(STEP_NAME)
                .<OrdersDTO, OrdersDTO>chunk(100)
                .reader(activeOrderReader())
                .processor(orderItemProcessor())
                .writer(orderWriter())
                .faultTolerant()
                .skip(MailSendException.class)
                .skipLimit(10)
                .build();
    }

    @Bean
    public ItemProcessor<OrdersDTO, OrdersDTO> orderItemProcessor() {

        return new OrderItemProcessor();
    }

    @Bean
    public ItemWriter<OrdersDTO> orderWriter() {
        return new OrderWriter();
    }

    @Bean
    public ItemReader<OrdersDTO> activeOrderReader() {

        String sql = "SELECT * FROM shipped_order WHERE status=1 and email_sent=0 and (email in('smecommerce2030@gmail.com')) ";
        return new JdbcCursorItemReaderBuilder<OrdersDTO>()
                .name("activeOrderReader")
                .sql(sql)
                .dataSource(dataSource)
                .rowMapper(new OrderMapper())
                .build();
    }
}
