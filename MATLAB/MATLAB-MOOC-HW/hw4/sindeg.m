function [sine_matrix,avg ] = sindeg(M)
radians_matrix = M ./ 360 .* 2*pi;
sine_matrix = sin(radians_matrix);
avg = mean(mean(sine_matrix,1));
end

