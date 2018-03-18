function [time_vector,miles_vector] = light_speed(distances)

time_vector = distances ./ 300000 ./ 60;
miles_vector = distances ./ 1.609;

end