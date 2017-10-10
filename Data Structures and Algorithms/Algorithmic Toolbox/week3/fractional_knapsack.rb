#!/usr/bin/env ruby
# by Ruochi Zhang

=begin
def find_max(weights,values)

  max_index = 0
  max_ratio = 0.0

  values.each_with_index do |value,index|
    if (value.to_f / weights[index]) > max_ratio
      max_ratio = (value.to_f / weights[index])
      max_index = index
    end
  end

  weight = weights.delete_at(max_index).to_f
  value = values.delete_at(max_index).to_f

  return weight, value
end




def get_optimal_value_naive(capacity, values, weights)
  sum = 0.0

  while capacity > 0
    weight, value = find_max(weights, values)
    a = [weight,capacity].min
    sum +=  (a * value / weight)
    capacity -= a
  end
  sum.round 4
end
=end

def get_sequence(values,weights)
  sequence = []
  values.each_with_index do |value, index|
    sequence.push([value.to_f,weights[index]])
  end
  sequence = sequence.sort { |a,b| a[0]/a[1] <=> b[0]/b[1]}.reverse
end


def get_optimal_value(capacity, values, weights)
  value = 0.0
  get_sequence(values,weights).each do |item|
    return value.round(4) if capacity.zero?
    a = [item[1],capacity].min
    value += (a*item[0]/item[1])
    capacity -= a
  end
end


=begin
def stress_test()

  capacity = 50
  values = [60,100,120]
  weights = [20,50,30]
  p get_optimal_value_naive(capacity,values.dup,weights.dup)
  p get_optimal_value(capacity,values,weights)
  puts ""


  n = 100
  values_list =[]
  weights_list = []

  n.times do
    values_list.push [(rand()*(200 - 100) + 100).to_i,(rand()*(200 - 100) + 100).to_i,(rand()*(200 - 100) + 100).to_i]
    weights_list.push [(rand()*(30 - 10) + 10).to_i,(rand()*(50 - 10) + 10).to_i,(rand()*(50 - 10) + 10).to_i]
  end

  

  #p weights_list
  #p values_list
  #exit


  n.times do |i|
    capacity = (rand()*(50 - 30) + 30).to_i
    #p capacity,values_list[i],weights_list[i]
    #p get_optimal_value_naive(capacity,values_list[i].dup,weights_list[i].dup)
    #p get_optimal_value(capacity,values_list[i],weights_list[i])
    
    puts ""
  end

end

stress_test()  
=end

if __FILE__ == $0

  data = STDIN.read.split().map(&:to_i)
  n, capacity = data[0,2]
  values = data.values_at(*(2..2*n).step(2))
  weights = data.values_at(*(3..2*n+1).step(2))

  answer = get_optimal_value(capacity, values, weights)
  puts "#{'%.4f' % answer}"

end
