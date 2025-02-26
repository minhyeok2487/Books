package junior.books.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import junior.books.exhandler.codes.ErrorCode;
import junior.books.exhandler.codes.ErrorCodeGroup;
import junior.books.exhandler.ErrorResponse;
import junior.books.utils.ApiErrorCodes;
import lombok.Builder;
import lombok.Getter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            Optional.ofNullable(handlerMethod.getMethodAnnotation(ApiErrorCodes.class))
                    .ifPresent(apiErrorCodes ->
                            generateErrorCodeResponseExample(
                                    operation,
                                    mergeErrorCodes(apiErrorCodes.codes(), apiErrorCodes.groups())
                            )
                    );
            return operation;
        };
    }

    private ErrorCode[] mergeErrorCodes(ErrorCode[] directCodes, ErrorCodeGroup[] groups) {
        return Stream.concat(
                Arrays.stream(directCodes),
                Arrays.stream(groups)
                        .flatMap(group -> Arrays.stream(group.getErrorCodes()))
        ).distinct().toArray(ErrorCode[]::new);
    }

    private void generateErrorCodeResponseExample(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                Arrays.stream(errorCodes)
                        .map(errorCode -> ExampleHolder.builder()
                                .holder(getSwaggerExample(errorCode))
                                .code(errorCode.getStatus().value())
                                .name(errorCode.name())
                                .build()
                        )
                        .collect(Collectors.groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(ErrorCode errorCode) {
        return new Example().value(
                ErrorResponse.of(
                        errorCode.getStatus().value(),
                        errorCode.name(),
                        errorCode.getMessage()
                )
        );
    }

    private void addExamplesToResponses(ApiResponses responses,
                                        Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach((status, v) -> {
            Content content = new Content();
            MediaType mediaType = new MediaType();
            ApiResponse apiResponse = new ApiResponse();

            v.forEach(exampleHolder -> mediaType.addExamples(
                    exampleHolder.getName(),
                    exampleHolder.getHolder()
            ));
            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);
            responses.addApiResponse(String.valueOf(status), apiResponse);
        });
    }
}

@Getter
@Builder
class ExampleHolder {

    private Example holder;
    private String name;
    private int code;
}
